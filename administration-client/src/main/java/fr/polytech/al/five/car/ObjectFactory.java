
package fr.polytech.al.five.car;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.polytech.al.five.car package. 
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

    private final static QName _NotExistingCarTypeException_QNAME = new QName("http://www.polytech.fr/al/five/car", "NotExistingCarTypeException");
    private final static QName _AlreadyExistingCarTypeException_QNAME = new QName("http://www.polytech.fr/al/five/car", "AlreadyExistingCarTypeException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.polytech.al.five.car
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NotExistingCarTypeException }
     * 
     */
    public NotExistingCarTypeException createNotExistingCarTypeException() {
        return new NotExistingCarTypeException();
    }

    /**
     * Create an instance of {@link AlreadyExistingCarTypeException }
     * 
     */
    public AlreadyExistingCarTypeException createAlreadyExistingCarTypeException() {
        return new AlreadyExistingCarTypeException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotExistingCarTypeException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polytech.fr/al/five/car", name = "NotExistingCarTypeException")
    public JAXBElement<NotExistingCarTypeException> createNotExistingCarTypeException(NotExistingCarTypeException value) {
        return new JAXBElement<NotExistingCarTypeException>(_NotExistingCarTypeException_QNAME, NotExistingCarTypeException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlreadyExistingCarTypeException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polytech.fr/al/five/car", name = "AlreadyExistingCarTypeException")
    public JAXBElement<AlreadyExistingCarTypeException> createAlreadyExistingCarTypeException(AlreadyExistingCarTypeException value) {
        return new JAXBElement<AlreadyExistingCarTypeException>(_AlreadyExistingCarTypeException_QNAME, AlreadyExistingCarTypeException.class, null, value);
    }

}
