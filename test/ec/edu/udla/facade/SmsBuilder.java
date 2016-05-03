package ec.edu.udla.facade;

import ec.edu.udla.domain.sms.Sms;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by moe on 5/3/16.
 */
public class SmsBuilder {


    @Test
    public void testElComandoParaEnvioDeSmsEs_M(){
        Sms sms = new Sms();
        assertTrue("M".equals(sms.comando()));
    }

}
