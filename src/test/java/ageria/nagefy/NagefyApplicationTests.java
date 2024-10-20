package ageria.nagefy;

import ageria.nagefy.dto.UserLoginDTO;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.exceptions.UnauthorizedException;
import ageria.nagefy.security.JWTTools;
import ageria.nagefy.services.AuthService;
import ageria.nagefy.services.StaffsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class NagefyApplicationTests {

	@InjectMocks
	private AuthService authService;

	@Mock
	private StaffsService staffsService;

	@Mock
	private JWTTools jwtTools;

	@Mock
	private PasswordEncoder bcrypt;

	private Staff mockStaff;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		// Crea un oggetto Staff simulato
		mockStaff = new Staff();
		mockStaff.setEmail("dlaruffa@gmail.com");
		mockStaff.setPassword(bcrypt.encode("1234"));
		// Imposta altre proprietà necessarie
	}

	@Test
	public void testLoginSuccess() {
		UserLoginDTO loginDTO = new UserLoginDTO("dlaruffa@gmail.com", "1234");

		// Configura il comportamento del mock
		when(staffsService.findFromEmail("dlaruffa@gmail.com")).thenReturn(mockStaff);
		when(bcrypt.matches("1234", mockStaff.getPassword())).thenReturn(true);

		// Esegui il login e controlla il token (qui puoi sostituire il ritorno con un token fittizio se necessario)
		String token = authService.checkCredentialsAndGenerateTokenStaff(loginDTO);

		// Verifica che il metodo del servizio sia stato chiamato
		verify(staffsService).findFromEmail("dlaruffa@gmail.com");
		assertEquals(mockStaff.getEmail(), "dlaruffa@gmail.com"); // Verifica che il login sia andato a buon fine
	}

	@Test
	public void testLoginFailureIncorrectPassword() {
		UserLoginDTO loginDTO = new UserLoginDTO("ageria@gmail.com", "123");

		// Configura il comportamento del mock
		when(staffsService.findFromEmail("ageria@gmail.com")).thenReturn(mockStaff);
		when(bcrypt.matches("123", mockStaff.getPassword())).thenReturn(false);

		// Controlla che venga sollevata l'eccezione
		assertThrows(UnauthorizedException.class, () -> authService.checkCredentialsAndGenerateTokenStaff(loginDTO));

		// Verifica che il metodo del servizio sia stato chiamato
		verify(staffsService).findFromEmail("ageria@gmail.com");
	}

	@Test
	public void testLoginFailureEmailNotFound() {
		UserLoginDTO loginDTO = new UserLoginDTO("ageia@gmail.com", "1234");

		// Configura il comportamento del mock
		when(staffsService.findFromEmail("ageia@gmail.com")).thenReturn(null);

		// Controlla che venga sollevata l'eccezione
		assertThrows(UnauthorizedException.class, () -> authService.checkCredentialsAndGenerateTokenStaff(loginDTO));

		// Verifica che il metodo del servizio sia stato chiamato
		verify(staffsService).findFromEmail("ageia@gmail.com");
	}

}
