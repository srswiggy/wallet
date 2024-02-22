package com.swiggy.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.wallet.entities.*;
import com.swiggy.wallet.exceptions.UserAlreadyExistsException;
import com.swiggy.wallet.exceptions.UserNotFoundException;
import com.swiggy.wallet.requestModels.UserRequestModel;
import com.swiggy.wallet.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.swiggy.wallet.responseModels.ResponseMessage.USER_DELETED_SUCCESSFULLY;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        reset(userService);
    }

    @Test
    void expectUserCreated() throws Exception {
        UserRequestModel userRequestModel = new UserRequestModel("testUser", "testPassword", Country.INDIA);
        User user = new User(1, "testUser", "testPassword", new Wallet(1, new Money(0.0, Currency.INR)), Country.INDIA);

        when(userService.register(userRequestModel)).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestModel)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("testUser"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.wallet.walletId").value(1))
                .andExpect(jsonPath("$.wallet.money.amount").value(0.0))
                .andExpect(jsonPath("$.wallet.money.currency").value("INR"))
                .andExpect(jsonPath("$.country").value("INDIA"));
        verify(userService, times(1)).register(userRequestModel);
    }

    @Test
    void expectUserAlreadyExists() throws Exception {
        UserRequestModel userRequestModel = new UserRequestModel("testUser","testPassword", Country.INDIA);

        when(userService.register(userRequestModel)).thenThrow(UserAlreadyExistsException.class);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestModel)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "user")
    void expectUserDeleted() throws Exception {
        when(userService.delete()).thenReturn(USER_DELETED_SUCCESSFULLY);

        mockMvc.perform(delete("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value(USER_DELETED_SUCCESSFULLY));
        verify(userService, times(1)).delete();
    }

    @Test
    @WithMockUser(username = "userNotFound")
    void expectUserNotFoundException() throws Exception {
        String username = "userNotFound";
        String errorMessage = "User "+username+" not be found.";

        when(userService.delete()).thenThrow(new UserNotFoundException(errorMessage));

        mockMvc.perform(delete("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(userService, times(1)).delete();
    }

    @Test
    void expectHttpMessageNotReadableException() throws Exception {
        UserRequestModel userRequestModel = new UserRequestModel("testUser", "testPassword", Country.INDIA);
        User user = new User(1, "testUser", "testPassword", new Wallet(1, new Money(0.0, Currency.INR)), Country.INDIA);

        when(userService.register(userRequestModel)).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\": \"testUser\", \"password\":\"testPassword\", \"country\": \"INDIAA\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Incorrect Request"))
                .andExpect(jsonPath("$.status").value(400));
    }
}
