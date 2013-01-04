package gr.neuropublic.gaia.invitation.ejb;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.sql.Date;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Set;


import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.dbutils.DbUtils;

import mutil.base.FileUtil;
import mutil.base.ExceptionAdapter;
import mutil.base.TreeNode;
import mutil.base.LRUCache;
import mutil.base.BooleanToggler;
import mutil.base.Pair;
import mutil.base.Util;
import mutil.base.RandomDateProcession;
import static mutil.base.Util.dateFromYYYYMMdd_type1;
import static mutil.base.Util.bdFromEnLocaleString;

import mutil.jpapersutil.JPUtil;

import gr.neuropublic.gaia.invitation.api.IRegisterEJB; 
import gr.neuropublic.gaia.invitation.api.InvitationStatus;

@Stateless
@Local (IRegisterEJB.ILocal.class)
@Remote(IRegisterEJB.IRemote.class)
public class RegisterEJB implements IRegisterEJB.ILocal, IRegisterEJB.IRemote{

    private static Logger l = LoggerFactory.getLogger(RegisterEJB.class);

    @Resource(mappedName="java:/invitation") DataSource dataSource;

    @Override
    public boolean register(String email, String firstname, String lastname, Timestamp memberSince) {
        return true;
    }
    
    @Override
    public InvitationStatus invitationStatus(String invId) throws SQLException {
        Connection        conn = null;
        PreparedStatement ps   = null;
        ResultSet         rs   = null;
        try {
            int now = Util.secondsSinceTheEpoch();
            boolean atLeastOneRow       = false;
            boolean haveBeenHereBefore  = false;
            int     valid_till          = Integer.MIN_VALUE;
            Integer accepted            = null;
            conn = dataSource.getConnection();
            ps = conn.prepareStatement( "select valid_till, accepted from invitation where guid = ?" );
            ps.setString(1, invId);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (haveBeenHereBefore) throw new RuntimeException(invId);
                haveBeenHereBefore = true;
                valid_till  = rs.getInt (1);
                accepted    = rs.getInt (2);
                atLeastOneRow = true;
            }
            if      ( accepted==null  &&  atLeastOneRow && now <= valid_till) return InvitationStatus.CURRENT;
            else if ( accepted==null  &&  atLeastOneRow && now >  valid_till) return InvitationStatus.EXPIRED;
            else if ( accepted==null  && !atLeastOneRow                     ) return InvitationStatus.NOT_FOUND;
            else if ( accepted!=null  &&  atLeastOneRow                     ) return InvitationStatus.ALREADY_ACCEPTED;
            else throw new RuntimeException(invId);
        } finally {
            DbUtils.closeQuietly(conn, ps, rs);
        }
    }
}

