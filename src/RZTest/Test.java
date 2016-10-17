package RZTest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.axis.encoding.Base64;
import org.junit.Before;

import com.cocopass.helper.CHttp;

public class Test {

	@Before
	public void setUp() throws Exception {
	}

	@org.junit.Test
	public void testRequestDoNetSoap() {
		String soapAction="http://tempuri.org/IService/";
		String operationName="AddData_ZL";
		Map<String, String> mapParms=new HashMap<String, String>();
		//byte[] packet=CreateSwitchEBikePowerPacket(terminalID,onORoff);
		//mapParms.put("dd", Base64.encode(packet));
		//fail("Not yet implemented");
		//Map<String, Object> mapParms=new HashMap<String, Object>();
		byte[] packet=new byte[]{29,29,56,(byte) 0d};
		//String response=CHttp.RequestDoNetSoap(url, soapAction, operationName, mapParms);
		
		mapParms.put("dd", Base64.encode(packet));

		com.cocopass.helper.CHttp.RequestDoNetSoap(
				"http://112.12.79.58:86/Service.svc",
				"http://tempuri.org/IService/", "AddData_ZL", mapParms);
	}

}
