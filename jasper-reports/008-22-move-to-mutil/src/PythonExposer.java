import java.sql.Connection;

import mutil.base.Triad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringEscapeUtils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.util.regex.Matcher; 
import java.util.regex.Pattern;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;
import java.io.InputStream;
import java.io.FileInputStream;

import org.apache.commons.lang3.StringUtils;


import mutil.base.ExceptionAdapter;
import mutil.base.Holder;
import mutil.base.FileUtil;

import org.apache.commons.io.FileUtils;

import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringEscapeUtils;
public class PythonExposer {

    private static final Logger l = LoggerFactory.getLogger(PythonExposer.class);

    private Connection conn = null;

    public PythonExposer(Connection conn) {
        this.conn = conn;
    }


    public Triad<Map<String, Object>, String, String> prepareParameters(String script) {
        String scriptUTFReady = escapeUTFForPythonScript(script);
        String scriptUTFReadyWtFuncs = StringUtils.join(PYTHON_HEADER, "\n")+"\n"+scriptUTFReady+"\n"+String.format(StringUtils.join(PYTHON_FOOTER, "\n"), "params");
        QueryJythonHolder sql = new QueryJythonHolder(conn);
        Map<String, Object> parameters = null;
        try {
            parameters = processParameters(sql, scriptUTFReadyWtFuncs);
        } catch (Exception e) {
            l.info("exception: ", e);
        }
        unescapeStringParameters(parameters);
        printParameters(parameters);
        return Triad.create(parameters, scriptUTFReadyWtFuncs, sql.getQueries());
    }


    private static void printParameters(Map<String, Object> params) {
        if (params!=null) {
            SortedSet<String> keys = new TreeSet<String>(params.keySet());
            for (String key : keys)
                l.info(String.format("%s --> %s", key, String.valueOf(params.get(key))));
        }
    }


    private static String escapeUTFForPythonScript(String script) {
        String escapedScript                  = StringEscapeUtils.escapeJava(script);
        String escapedScriptNewLinesCorrected = escapedScript.replaceAll("\\\\n", "\n");
        String escapedScriptQuotesCorrected   = escapedScriptNewLinesCorrected.replaceAll("\\\\'", "\'");
               escapedScriptQuotesCorrected   = escapedScriptQuotesCorrected.replaceAll("\\\\\"", "\"");
        return escapedScriptQuotesCorrected;
    }


    private static Map<String, Object> processParameters(QueryJythonHolder sql, String script) throws PyException {
        Map<String, Object> params = new HashMap<String, Object>();
        {
            PythonInterpreter pi = new PythonInterpreter();
            pi.set("params", params);
            pi.set("sql", sql);
            pi.set("u", new JythonUtils());
            PyObject result = null;

            PyCode code = pi.compile(script);
            result = pi.eval(code);
            l.info("evalution result = "+result);
        }
        return params;
    }

    private static void unescapeStringParameters(Map<String, Object> params) {
        if (params != null)
            for (String key : params.keySet())
                if (key.startsWith("STR_"))
                    params.put(key, StringEscapeUtils.unescapeJava((String)params.get(key)));
    }


    private static final String PYTHON_HEADER[] = {
        //        "# -*- coding: utf-8 -*-",
        "import sys",
        "import re",
        "import math",
        "from datetime import date",
        "",
        "def eq(x):",
        "    return x",
        "",
        "def ceiling(x):",
        "    return int(math.ceil(x))",
        "",
        "def materialize(prefix, ehm, colCode, colValue, f=eq):",
        "    for i in range(ehm.len()):",
        //        "        u.println('getting data on row %d of %d'%(i,ehm.len()))",
        //        "        tempDict = ehm[i+1]",
        //        "        for tempDictK in tempDict.keys():",
        //        "            print 'key: %s --> %s'%(tempDictK, tempDict[tempDictK])",
        //        "        for tempDictKn in tempDict.keysN():",
        //        "            print 'key: %s --> %s'%(tempDictKn, tempDict.f(tempDictKn))",
        "        code  = ehm[i+1].f(colCode)",
        "        value = f(ehm[i+1].f(colValue))",
        "        globalName = prefix+code",
        "        globals()[globalName]=value",
        "        u.println('declared global %s with value: %s' % (globalName, value))",
        "",
        "REPORT_PARAMS = re.compile(\"(STR|BD|I|D)_\\w*\")",
        "",
        "def NVL(v, default):",
        " if (v==None):",
        "  return default",
        " else:",
        "  return v",
        "",
        "def NVLZ(v):",
        " return NVL(v, 0)",
        "",
        "def isReportGlobal(s):",
        "    return REPORT_PARAMS.match(s)",
        "",
        "class MyRecord:",
        "    def __init__(self, dictData, calledFromSqlS=False):",
        "        self._dictData = dictData",
        "        if calledFromSqlS:",
        "            for k in dictData.internal.keySet():",
        "                kUpper = k.upper()",
        //        "                u.println('examining: %s '%kUpper)",
        "                if isReportGlobal(kUpper):",
        //        "                    u.println('adding SCRIPT-GLOBAL-PARAM : %s with value %s'%(kUpper, dictData.f(k)))",
        "                    globals()[kUpper]=dictData.f(k)",
        "    def __getattr__(self, nameOfField):",
        //        "        global STR_debug",
        //        "        STR_debug = nameOfField",
        //        "        u.println('**** accessing field: %s'%nameOfField)",
        "        return self._dictData.f(nameOfField)",
        "    def __getitem__(self, idx):",
        "        return self._dictData[idx]",
        "    def keys(self):",
        "        return self._dictData.keySet()",
        "    def keysN(self):",
        "        return self._dictData.internal.keySet()",
        "    def f(self, name):",
        //        "        u.println('calling f with name = % s'%name)",
        "        return self._dictData.f(name)",
        "",
        "class MySQLrecords:",
        "    def __init__(self, sqlData):",
        "        self._results = sqlData",
        "    def __getitem__(self, idx):",
        "        return MyRecord(self._results[idx])",
        "    def len(self):",
        "        return self._results.size()",
        "",
        "def sqlm(query, num=20, panicIfLess=False, panicIfMore=False):",
        "    return MySQLrecords(sql.sqlm(query, num, panicIfLess, panicIfMore))",
        "",
        "def sqls(query):",
        "    return MyRecord(sql.sqls(query), True)"
        };


    private static final String PYTHON_FOOTER[] = {
        "",
        "for (k,v) in globals().items():",
        "    if isReportGlobal(k):",
        "        %s.put(k, v)"
        };

}