package api.Payloads.JsonFullPayload;

public class JsonPayloads {

	public static String updatePlace(String place_id) {
		return "{\r\n" + "\"place_id\":\"" + place_id + "\",\r\n" + "\"address\":\"702 winter walk, USA\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n" + "}";
	}

	public static String loanDirsbument(String bindingId, String TrxId)

	{
		return "{\r\n" + " \"bindingId\": \"" + bindingId + "\",\r\n" + "  \"partnerTrxId\": \"" + TrxId + "\",\r\n"
				+ "  \"disbursementAmount\": 500000,\r\n" + "  \"term\": 3,\r\n" + "  \"productType\": 1\r\n" + "}\r\n"
				+ "";

	}
}
