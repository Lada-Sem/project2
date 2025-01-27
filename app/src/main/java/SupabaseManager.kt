import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.auth.providers.builtin.Email


class SupabaseManager {
    val supa = createSupabaseClient(
        supabaseUrl = "https://nmltsowqnfengqahngas.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5tbHRzb3dxbmZlbmdxYWhuZ2FzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzczNjcxODgsImV4cCI6MjA1Mjk0MzE4OH0.XS4aF3nUHxG69ZnIBL-H_nqxl7pniBCNtnEN6c0SaeY"
    ) {
        install(Postgrest)
        install(Auth)

    }
    suspend fun signINOTP(mail: String){
        supa.auth.signInWith(OTP){
            email = mail
        }

    }
    suspend fun signIn(mail: String,pass: String) {
        supa.auth.signInWith(Email) {
            email = mail
            password = pass
        }
    }

    suspend fun signUp(mail: String, pass: String, phone: String, name:String){
        supa.auth.signUpWith(Email) {
            email = email
            password = pass
        }

        val user = supa.auth.sessionManager.loadSession()?.user?.id
        val newUser = mapOf(
            "id" to user,
            "balance" to 0.00f,
            "is_courier" to false,
            "name" to name,
            "phone_number" to phone.toLong()
        )

        supa.from("users").insert(newUser)
    }
    suspend fun resetpass(mail: String) {
        supa.auth.resetPasswordForEmail(mail)
    }
    suspend fun sendOTP(mail: String, code:String) {
        supa.auth.verifyEmailOtp(OtpType.Email.EMAIL, mail, code)
    }

}





