package fr.polytech.al.five.exceptions;

import javax.xml.ws.WebFault;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@WebFault(name = "NotExistingCarType")
public class NotExistingCarType extends Exception {
}
