package gr.gsis.wsnp;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RgWsBasStoixNRtUser complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RgWsBasStoixNRtUser">
 *   &lt;complexContent>
 *     &lt;extension base="{http://gr/gsis/rgwsbasstoixn/RgWsBasStoixN.wsdl/types/}RgWsBasStoixNRtBase">
 *       &lt;sequence>
 *         &lt;element name="actLongDescr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="postalZipCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facActivity" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="registDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="stopDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="doyDescr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="deactivationFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="postalAddressNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="postalAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="doy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firmPhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="onomasia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firmFax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="afm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="commerTitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RgWsBasStoixNRtUser", propOrder = {
    "actLongDescr",
    "postalZipCode",
    "facActivity",
    "registDate",
    "stopDate",
    "doyDescr",
    "parDescription",
    "deactivationFlag",
    "postalAddressNo",
    "postalAddress",
    "doy",
    "firmPhone",
    "onomasia",
    "firmFax",
    "afm",
    "commerTitle"
})
public class RgWsBasStoixNRtUser
    extends RgWsBasStoixNRtBase
{

    @XmlElement(required = true, nillable = true)
    protected String actLongDescr;
    @XmlElement(required = true, nillable = true)
    protected String postalZipCode;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal facActivity;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected String registDate;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected String stopDate;
    @XmlElement(required = true, nillable = true)
    protected String doyDescr;
    @XmlElement(required = true, nillable = true)
    protected String parDescription;
    @XmlElement(required = true, nillable = true)
    protected String deactivationFlag;
    @XmlElement(required = true, nillable = true)
    protected String postalAddressNo;
    @XmlElement(required = true, nillable = true)
    protected String postalAddress;
    @XmlElement(required = true, nillable = true)
    protected String doy;
    @XmlElement(required = true, nillable = true)
    protected String firmPhone;
    @XmlElement(required = true, nillable = true)
    protected String onomasia;
    @XmlElement(required = true, nillable = true)
    protected String firmFax;
    @XmlElement(required = true, nillable = true)
    protected String afm;
    @XmlElement(required = true, nillable = true)
    protected String commerTitle;

    public String getActLongDescr() {
        return actLongDescr;
    }

    public void setActLongDescr(String value) {
        this.actLongDescr = value;
    }

    public String getPostalZipCode() {
        return postalZipCode;
    }

    public void setPostalZipCode(String value) {
        this.postalZipCode = value;
    }

    public BigDecimal getFacActivity() {
        return facActivity;
    }

    public void setFacActivity(BigDecimal value) {
        this.facActivity = value;
    }

    public String getRegistDate() {
        return registDate;
    }

    public void setRegistDate(String value) {
        this.registDate = value;
    }

    public String getStopDate() {
        return stopDate;
    }


    public void setStopDate(String value) {
        this.stopDate = value;
    }

    public String getDoyDescr() {
        return doyDescr;
    }

    public void setDoyDescr(String value) {
        this.doyDescr = value;
    }

    public String getParDescription() {
        return parDescription;
    }

    public void setParDescription(String value) {
        this.parDescription = value;
    }

    public String getDeactivationFlag() {
        return deactivationFlag;
    }

    public void setDeactivationFlag(String value) {
        this.deactivationFlag = value;
    }

    public String getPostalAddressNo() {
        return postalAddressNo;
    }

    public void setPostalAddressNo(String value) {
        this.postalAddressNo = value;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String value) {
        this.postalAddress = value;
    }

    public String getDoy() {
        return doy;
    }

    public void setDoy(String value) {
        this.doy = value;
    }

    public String getFirmPhone() {
        return firmPhone;
    }

    public void setFirmPhone(String value) {
        this.firmPhone = value;
    }

    public String getOnomasia() {
        return onomasia;
    }

    public void setOnomasia(String value) {
        this.onomasia = value;
    }

    public String getFirmFax() {
        return firmFax;
    }

    public void setFirmFax(String value) {
        this.firmFax = value;
    }

    public String getAfm() {
        return afm;
    }

    public void setAfm(String value) {
        this.afm = value;
    }

    public String getCommerTitle() {
        return commerTitle;
    }

    public void setCommerTitle(String value) {
        this.commerTitle = value;
    }

}
