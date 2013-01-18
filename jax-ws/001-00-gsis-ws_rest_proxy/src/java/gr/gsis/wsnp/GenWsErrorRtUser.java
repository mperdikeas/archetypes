package gr.gsis.wsnp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GenWsErrorRtUser complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GenWsErrorRtUser">
 *   &lt;complexContent>
 *     &lt;extension base="{http://gr/gsis/rgwsbasstoixn/RgWsBasStoixN.wsdl/types/}GenWsErrorRtBase">
 *       &lt;sequence>
 *         &lt;element name="errorDescr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenWsErrorRtUser", propOrder = {
    "errorDescr",
    "errorCode"
})
public class GenWsErrorRtUser
    extends GenWsErrorRtBase
{

    @XmlElement(required = true, nillable = true)
    protected String errorDescr;
    @XmlElement(required = true, nillable = true)
    protected String errorCode;

    public String getErrorDescr() {
        return errorDescr;
    }

    public void setErrorDescr(String value) {
        this.errorDescr = value;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String value) {
        this.errorCode = value;
    }

}
