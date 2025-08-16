# Secure-Wi-Fi-Authentication-with-OTP
## 📖 Abstract
With the growing need for secure authentication mechanisms, OTP-based Wi-Fi access offers a robust solution to ensure authorized usage.  
This system eliminates risks associated with static passwords by implementing a dynamically generated OTP that is valid for a limited time.  

The project is developed in **Java** and leverages:
- **AES encryption** for secure OTP storage  
- **HTTP-based API communication** for SMS transmission via **Twilio**  
- **HashMap-based temporary storage** for OTP validation  

Users receive an OTP on their registered mobile number, which must be entered within **5 minutes** to gain access to the Wi-Fi network.  
This method enhances security, prevents unauthorized access, and ensures a seamless user experience.

---

## ⚡ Features
- ✅ Dynamic 6-digit OTP generation  
- ✅ AES-based OTP encryption & decryption  
- ✅ SMS delivery via Twilio API  
- ✅ OTP expiry within 5 minutes  
- ✅ Secure in-memory OTP storage  
- ✅ Java-based lightweight implementation  

---

## 🛠️ Tech Stack
- **Programming Language:** Java  
- **Cryptography:** AES (Advanced Encryption Standard)  
- **Networking:** HTTP (Java HttpURLConnection)  
- **API Service:** Twilio SMS API  
- **Data Storage:** In-memory HashMap  
- **IDE Support:** VS Code / IntelliJ IDEA / Eclipse  

---

## ⚙️ Setup & Installation

### 1. Clone the Repository
git clone https://github.com/Pravallika-Yadlapalli/Secure-Wi-Fi-Authentication-with-OTP.git
cd Secure-Wi-Fi-Authentication-with-OTP
2. Configure Twilio Credentials
Do NOT hardcode your credentials in the code. Instead, set them as environment variables:

**For Windows (PowerShell):**

setx ACCOUNT_SID "your_twilio_account_sid"
setx AUTH_TOKEN "your_twilio_auth_token"
setx TWILIO_PHONE_NUMBER "+1234567890"
**For Linux/Mac (bash):**

export ACCOUNT_SID="your_twilio_account_sid"
export AUTH_TOKEN="your_twilio_auth_token"
export TWILIO_PHONE_NUMBER="+1234567890"
**3. Compile & Run**
javac Otp1.java
java Otp1
**▶️ Usage**
Run the program in your IDE or terminal.

Enter your phone number when prompted.

The system will generate a 6-digit OTP, encrypt it, and send it via Twilio SMS.

Enter the OTP you received on your mobile.

If valid (and within 5 minutes), Wi-Fi access is granted.

📂 **Project Structure**
Secure-Wi-Fi-Authentication-with-OTP/
│── Otp1.java          # Main source code
│── README.md          # Project documentation
│── docs/              # (Optional) Extra project report, diagrams
🔒 Security Notes
Never push your Twilio credentials (ACCOUNT_SID, AUTH_TOKEN) to GitHub.

Always use environment variables or a .env file (excluded via .gitignore).

Twilio provides free trial accounts for testing purposes.

📜**License**
This project was developed for academic purposes.
You may freely use or modify it for learning and research.

