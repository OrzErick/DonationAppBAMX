package mx.tec.donationapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Before
import org.junit.Test

class LoginTest {
    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        // Inicializa el objeto UiDevice
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    @Throws(UiObjectNotFoundException::class)
    fun testLogin() {
        // Ingresa el nombre de usuario y contraseña
        val emailEditText: UiObject2? = device.findObject(By.res("mx.tec.donationapp:id/EmailAdresseditText"))
        val passwordEditText: UiObject2? = device.findObject(By.res("mx.tec.donationapp:id/PasswordeditText"))

        emailEditText?.text = "pruebas3@tec.mx"
        passwordEditText?.text = "123456"

        // Cierra el teclado virtual
        device.pressBack()

        // Realiza clic en el botón de inicio de sesión
        val loginButton: UiObject2? = device.findObject(By.res("mx.tec.donationapp:id/IniciarSesionbutton"))
        loginButton?.click()

        // Espera a que aparezca el Toast "LOGIN EXITOSO" (hasta 20 segundos)
        val toastText = "LOGIN EXITOSO"
        val timeoutMillis = 20000 // Espera hasta 20 segundos para el Toast
        val toastObject = device.wait(Until.hasObject(By.text(toastText)), timeoutMillis.toLong())

        // Verifica que se haya mostrado el Toast "LOGIN EXITOSO"
        if (toastObject == null) {
            throw AssertionError("El Toast \"$toastText\" no se mostró dentro del tiempo especificado")
        }
    }

}
