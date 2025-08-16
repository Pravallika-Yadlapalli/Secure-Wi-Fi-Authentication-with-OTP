import javax.crypto.Cipher; 
import javax.crypto.KeyGenerator; 
import javax.crypto.SecretKey; 
import java.io.OutputStream; 
import java.net.HttpURLConnection; 
import java.net.URL; 
import java.util.Base64; 
import java.util.HashMap; 
import java.util.Map; 
import java.util.Random; 
import java.util.Scanner; 
public class Otp1 { 
private static SecretKey secretKey; 
private static final Map<String, OTPData> otpMap = new HashMap<>(); 
// Twilio Credentials (Replace these with your actual credentials) 
// Twilio Credentials (from environment variables)
private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
private static final String TWILIO_PHONE_NUMBER = System.getenv("TWILIO_PHONE_NUMBER");
// Generate AES Key 
static { 
try { 
KeyGenerator keyGenerator = KeyGenerator.getInstance("AES"); 
keyGenerator.init(128); 
secretKey = keyGenerator.generateKey(); 
} catch (Exception e) { 
throw new RuntimeException("Error generating AES Key", e); 
} 
} 
// Encrypt OTP using AES 
public static String encrypt(String otp) throws Exception { 
Cipher cipher = Cipher.getInstance("AES"); 
cipher.init(Cipher.ENCRYPT_MODE, secretKey); 
byte[] encryptedBytes = cipher.doFinal(otp.getBytes()); 
return Base64.getEncoder().encodeToString(encryptedBytes); 
} 
// Decrypt OTP 
public static String decrypt(String encryptedOtp) throws Exception { 
Cipher cipher = Cipher.getInstance("AES"); 
cipher.init(Cipher.DECRYPT_MODE, secretKey); 
byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedOtp)); 
return new String(decryptedBytes); 
} 
// Store OTP in memory 
public static void storeOtp(String phone, String otp) throws Exception { 
String encryptedOtp = encrypt(otp); 
otpMap.put(phone, new OTPData(encryptedOtp, System.currentTimeMillis())); 
System.out.println(" OTP stored securely (Encrypted)."); 
} 
// Verify OTP 
public static boolean verifyOtp(String phone, String enteredOtp) throws Exception { 
if (!otpMap.containsKey(phone)) { 
System.out.println(" OTP not found."); 
return false; 
} 
OTPData otpData = otpMap.get(phone); 
long currentTime = System.currentTimeMillis(); 
// Check if OTP is expired (5 minutes) 
if ((currentTime - otpData.timestamp) > (5 * 60 * 1000)) { 
System.out.println(" OTP Expired!"); 
otpMap.remove(phone); 
return false; 
} 
// Decrypt and compare OTP 
String decryptedOtp = decrypt(otpData.otp); 
return decryptedOtp.equals(enteredOtp); 
} 
// Send OTP via Twilio API (Without SDK) 
public static void sendOtp(String phone, String otp) { 
try { 
String url = "https://api.twilio.com/2010-04-01/Accounts/" + ACCOUNT_SID + 
"/Messages.json"; 
String authStr = ACCOUNT_SID + ":" + AUTH_TOKEN; 
String authEncoded = Base64.getEncoder().encodeToString(authStr.getBytes()); 
// Create HTTP request body 
String postData = "To=" + phone + 
"&From=" + TWILIO_PHONE_NUMBER + 
"&Body=" + "Your OTP is: " + otp + ". It will expire in 5 minutes."; 
// Open connection 
URL obj = new URL(url); 
HttpURLConnection conn = (HttpURLConnection) obj.openConnection(); 
conn.setRequestMethod("POST"); 
conn.setRequestProperty("Authorization", "Basic " + authEncoded); 
conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
conn.setDoOutput(true); 
// Send data 
OutputStream os = conn.getOutputStream(); 
os.write(postData.getBytes()); 
os.flush(); 
os.close(); 
// Get response 
int responseCode = conn.getResponseCode(); 
if (responseCode == 201) { 
System.out.println("OTP sent successfully to: " + phone); 
} else { 
System.out.println("Failed to send OTP. Response Code: " + responseCode); 
} 
} catch (Exception e) { 
e.printStackTrace(); 
} 
} 
// Main Method 
public static void main(String[] args) { 
Scanner scanner = new Scanner(System.in); 
System.out.print(" Enter Phone Number: "); 
String phone = scanner.nextLine(); 
// Generate 6-digit OTP 
String otp = String.valueOf(100000 + new Random().nextInt(900000)); 
System.out.println("Generated OTP: " + otp); 
try { 
// Store encrypted OTP 
storeOtp(phone, otp); 
// Send OTP via Twilio (Without SDK) 
sendOtp(phone, otp); 
// Verify OTP 
System.out.print(" Enter received OTP: "); 
String enteredOtp = scanner.nextLine(); 
if (verifyOtp(phone, enteredOtp)) { 
System.out.println(" OTP Verified Successfully!"); 
} else { 
System.out.println(" Invalid or Expired OTP."); 
} 
} catch (Exception e) { 
e.printStackTrace(); 
} 
scanner.close(); 
} 
// Class to store OTP & timestamp 
static class OTPData { 
String otp; 
long timestamp; 
OTPData(String otp, long timestamp) { 
this.otp = otp; 
this.timestamp = timestamp; 
} 
} 
}