package fr.polytech.al.five.exceptions;

import javax.xml.ws.WebFault;

@WebFault(targetNamespace = "http://www.polytech.fr/al/five/car")
public class AlreadyExistingCarTypeException extends Exception {
}
