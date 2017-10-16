
package fr.polytech.al.five;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.polytech.al.five package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ModifyPriorityResponse_QNAME = new QName("http://webservices.five.al.polytech.fr/", "modifyPriorityResponse");
    private final static QName _RegisterPriorityResponse_QNAME = new QName("http://webservices.five.al.polytech.fr/", "registerPriorityResponse");
    private final static QName _ModifyPriority_QNAME = new QName("http://webservices.five.al.polytech.fr/", "modifyPriority");
    private final static QName _RegisterPriority_QNAME = new QName("http://webservices.five.al.polytech.fr/", "registerPriority");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.polytech.al.five
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ModifyPriority }
     * 
     */
    public ModifyPriority createModifyPriority() {
        return new ModifyPriority();
    }

    /**
     * Create an instance of {@link RegisterPriority }
     * 
     */
    public RegisterPriority createRegisterPriority() {
        return new RegisterPriority();
    }

    /**
     * Create an instance of {@link ModifyPriorityResponse }
     * 
     */
    public ModifyPriorityResponse createModifyPriorityResponse() {
        return new ModifyPriorityResponse();
    }

    /**
     * Create an instance of {@link RegisterPriorityResponse }
     * 
     */
    public RegisterPriorityResponse createRegisterPriorityResponse() {
        return new RegisterPriorityResponse();
    }

    /**
     * Create an instance of {@link CarType }
     * 
     */
    public CarType createCarType() {
        return new CarType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyPriorityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.five.al.polytech.fr/", name = "modifyPriorityResponse")
    public JAXBElement<ModifyPriorityResponse> createModifyPriorityResponse(ModifyPriorityResponse value) {
        return new JAXBElement<ModifyPriorityResponse>(_ModifyPriorityResponse_QNAME, ModifyPriorityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterPriorityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.five.al.polytech.fr/", name = "registerPriorityResponse")
    public JAXBElement<RegisterPriorityResponse> createRegisterPriorityResponse(RegisterPriorityResponse value) {
        return new JAXBElement<RegisterPriorityResponse>(_RegisterPriorityResponse_QNAME, RegisterPriorityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyPriority }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.five.al.polytech.fr/", name = "modifyPriority")
    public JAXBElement<ModifyPriority> createModifyPriority(ModifyPriority value) {
        return new JAXBElement<ModifyPriority>(_ModifyPriority_QNAME, ModifyPriority.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterPriority }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.five.al.polytech.fr/", name = "registerPriority")
    public JAXBElement<RegisterPriority> createRegisterPriority(RegisterPriority value) {
        return new JAXBElement<RegisterPriority>(_RegisterPriority_QNAME, RegisterPriority.class, null, value);
    }

}
