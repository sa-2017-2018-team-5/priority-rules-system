package arquillian;

import fr.polytech.al.five.components.PriorityRegister;
import fr.polytech.al.five.components.RoutePlanner;
import fr.polytech.al.five.components.RouteRegister;
import fr.polytech.al.five.components.TrafficSupervisionClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public abstract class AbstractPRSTest {

    @Deployment
    public static WebArchive createDeployment() {
        // Building a Web ARchive (WAR) containing the following elements:
        return ShrinkWrap.create(WebArchive.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")

                .addPackage(PriorityRegister.class.getPackage())
                .addPackage(RoutePlanner.class.getPackage())
                .addPackage(RouteRegister.class.getPackage())
                .addPackage(TrafficSupervisionClient.class.getPackage())

                // Persistence file.
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");
    }

}
