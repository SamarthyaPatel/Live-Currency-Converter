import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/***
 * <p> File name: currencyRate.java</p>
 * <p> Creation date: 01.04.2022</p>
 * <p> Last modification date: 13.04.2022</p>
 * <p> Purpose of the program: Handling the process of fetching currency rates from web and providing data to appropriate files.</p>
 * <p> Version history: 1.0 - pure code; 2.0 - comments added</p>
 * 
 * @author Samarthya Patel
 * @version 2.0
 */

public class CurrencyRate {

	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWENTY = 20;
	private static final String LINK = "https://www.google.com/finance/quote/";
	private static final String HYPHEN = "-";
	
	//lhs for left hand side currency
	//rhs for right hand side currency
	private String lhs;
	private String rhs;
	
	/***
	 * Constructor to create Currency Rate object.
	 * 
	 * @param lhs
	 * @param rhs
	 */
	public CurrencyRate(String lhs, String rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	/***
	 * Get method to get left hand side Currency.
	 * 
	 * @return lhs
	 */
	public String getLHS() {
		return lhs;
	}
	
	/***
	 * Get method to get Right hand side Currency.
	 * 
	 * @return rhs
	 */
	public String getRHS() {
		return rhs;
	}
	
	/***
	 * Get method to get the live currency rate of given currencies from the web.
	 * 
	 * @return value of the currency rate.
	 * @throws IOException
	 */
	public double getCurrencyRate() throws IOException {
		
		double value = ZERO;
		
		//if both of the currencies are same than return 1 else get the value from web and retun it.
		if(getLHS().equals(getRHS())) { 
			
			return ONE;
		
		} else {
			
			URL url = new URL(LINK + getLHS() + HYPHEN + getRHS());
			URLConnection con = url.openConnection();
			
			InputStreamReader instream = new InputStreamReader(con.getInputStream());
			
			BufferedReader buff = new BufferedReader(instream);
			
			String line = buff.readLine();
			
			while(line != null) {
				
				//Data refining for getting the exact value
				if(line.contains("\"" + getLHS() + " / " + getRHS() + "\"")) {
					
					int position = line.indexOf("\"" + getLHS() + " / " + getRHS() + "\"");
					int end = line.indexOf(",", position + TWENTY);
					int start = line.indexOf("[", position) + ONE;
					value = Double.parseDouble(line.substring(start, end));
					break;
					
				} else {
					line = buff.readLine();
				}
			}
			
			instream.close();
			buff.close();
			
			return value;
		}
	}
}
