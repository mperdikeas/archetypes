
package gr.gsis.rgwsbasstoixn.rgwsbasstoixn;

import java.math.BigDecimal;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import gr.gsis.rgwsbasstoixn.rgwsbasstoixn_wsdl.types.GenWsErrorRtUser;
import gr.gsis.rgwsbasstoixn.rgwsbasstoixn_wsdl.types.ObjectFactory;
import gr.gsis.rgwsbasstoixn.rgwsbasstoixn_wsdl.types.RgWsBasStoixEpitRtUser;
import gr.gsis.rgwsbasstoixn.rgwsbasstoixn_wsdl.types.RgWsBasStoixNRtUser;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "RgWsBasStoixN", targetNamespace = "http://gr/gsis/rgwsbasstoixn/RgWsBasStoixN.wsdl")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface RgWsBasStoixN {


    /**
     * 
     * @param pAfm
     * @param pBasStoixEpitRecOut
     * @param pCallSeqIdOut
     * @param pErrorRecOut
     */
    @WebMethod(action = "http://gr/gsis/rgwsbasstoixn/RgWsBasStoixN.wsdl/rgWsBasStoixEpit")
    public void rgWsBasStoixEpit(
        @WebParam(name = "pAfm", partName = "pAfm")
        String pAfm,
        @WebParam(name = "pBasStoixEpitRec_out", mode = WebParam.Mode.INOUT, partName = "pBasStoixEpitRec_out")
        Holder<RgWsBasStoixEpitRtUser> pBasStoixEpitRecOut,
        @WebParam(name = "pCallSeqId_out", mode = WebParam.Mode.INOUT, partName = "pCallSeqId_out")
        Holder<BigDecimal> pCallSeqIdOut,
        @WebParam(name = "pErrorRec_out", mode = WebParam.Mode.INOUT, partName = "pErrorRec_out")
        Holder<GenWsErrorRtUser> pErrorRecOut);

    /**
     * 
     * @param pAfm
     * @param pBasStoixNRecOut
     * @param pCallSeqIdOut
     * @param pErrorRecOut
     */
    @WebMethod(action = "http://gr/gsis/rgwsbasstoixn/RgWsBasStoixN.wsdl/rgWsBasStoixN")
    public void rgWsBasStoixN(
        @WebParam(name = "pAfm", partName = "pAfm")
        String pAfm,
        @WebParam(name = "pBasStoixNRec_out", mode = WebParam.Mode.INOUT, partName = "pBasStoixNRec_out")
        Holder<RgWsBasStoixNRtUser> pBasStoixNRecOut,
        @WebParam(name = "pCallSeqId_out", mode = WebParam.Mode.INOUT, partName = "pCallSeqId_out")
        Holder<BigDecimal> pCallSeqIdOut,
        @WebParam(name = "pErrorRec_out", mode = WebParam.Mode.INOUT, partName = "pErrorRec_out")
        Holder<GenWsErrorRtUser> pErrorRecOut);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "http://gr/gsis/rgwsbasstoixn/RgWsBasStoixN.wsdl/rgWsBasStoixNVersionInfo")
    @WebResult(name = "result", partName = "result")
    public String rgWsBasStoixNVersionInfo();

}
