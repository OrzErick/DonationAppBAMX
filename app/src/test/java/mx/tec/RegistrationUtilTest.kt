package mx.tec
import mx.tec.donationapp.RegistrationUtil
import org.junit.Assert
import org.junit.Test

class RegistrationUtilTest {

    @Test
    fun `empty input returns false`() {
        val result = RegistrationUtil.validRegistrationInput("", "", "")
        Assert.assertFalse(result)
    }

    @Test
    fun `valid username and correctly formatted password returns true`() {
        val result = RegistrationUtil.validRegistrationInput("NewUser", "Password123", "Password123")
        Assert.assertTrue(result)
    }

    @Test
    fun `username already taken returns false`() {
        val result = RegistrationUtil.validRegistrationInput("Rahul", "Password123", "Password123")
        Assert.assertFalse(result)
    }

    @Test
    fun `passwords do not match returns false`() {
        val result = RegistrationUtil.validRegistrationInput("NewUser", "Password123", "DifferentPassword")
        Assert.assertFalse(result)
    }

    @Test
    fun `password with less than 2 digits returns false`() {
        val result = RegistrationUtil.validRegistrationInput("NewUser", "Password", "Password")
        Assert.assertFalse(result)
    }
}