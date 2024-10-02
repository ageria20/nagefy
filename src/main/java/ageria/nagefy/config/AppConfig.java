package ageria.nagefy.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public Cloudinary imageUploader(@Value("${cloudinary.name}") String name,
                                    @Value("${cloudinary.key}") String key,
                                    @Value("${cloudinary.secret}") String secret) {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", name);
        config.put("api_key", key);
        config.put("api_secret", secret);
        return new Cloudinary(config);
    }




    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

}
