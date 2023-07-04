package de.neuefische.backend;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ThemeControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ThemeRepo themeRepo;

    String expectedSummer = "https://cdn.discordapp.com/attachments/1090325789939085312/1123893755842412616/00001" +
            "-918857782.png";
    String expectedAutumn = "https://cdn.discordapp.com/attachments/1090325789939085312/1123893768257540137/00043" +
            "-3644440715.png";
    String expectedWinter = "https://cdn.discordapp.com/attachments/1090325789939085312/1123893783323488316/00006" +
            "-1847996727.png";
    String expectedSpring = "https://cdn.discordapp.com/attachments/1090325789939085312/1123893739421708328/00038" +
            "-162447185.png";


    String testDTOThemeJson = """
                           {
                               "name": "Test Theme",
                               "springUrl": "https://cdn.discordapp.com/attachments/1090325789939085312/1123893739421708328/00038-162447185.png",
                               "summerUrl": "https://cdn.discordapp.com/attachments/1090325789939085312/1123893755842412616/00001-918857782.png",
                               "autumnUrl": "https://cdn.discordapp.com/attachments/1090325789939085312/1123893768257540137/00043-3644440715.png",
                               "winterUrl": "https://cdn.discordapp.com/attachments/1090325789939085312/1123893783323488316/00006-1847996727.png",
                               "seasonStatus": "SUMMER"
                            }
                        """;

    String testThemeJson = """
                           {
                               "id": "12344445",
                               "name": "Theme Theme",
                               "springUrl": "https://cdn.discordapp.com/attachments/1090325789939085312/1123893739421708328/00038-162447185.png",
                               "summerUrl": "https://cdn.discordapp.com/attachments/1090325789939085312/1123893755842412616/00001-918857782.png",
                               "autumnUrl": "https://cdn.discordapp.com/attachments/1090325789939085312/1123893768257540137/00043-3644440715.png",
                               "winterUrl": "https://cdn.discordapp.com/attachments/1090325789939085312/1123893783323488316/00006-1847996727.png",
                               "seasonStatus": "WINTER"
                            }
                        """;

    @Test
    @DirtiesContext
    void expectListOfThemes_whenGetThemes() throws Exception {

        //GIVEN

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/theme"))
            //THEN
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].summerUrl").value(expectedSummer))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].autumnUrl").value(expectedAutumn))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].winterUrl").value(expectedWinter))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].springUrl").value(expectedSpring));
    }

    @Test
    @DirtiesContext
    void expectUpdatedList_whenAddingTheme() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/theme")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testDTOThemeJson)
        )
        //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Test Theme"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].summerUrl").value(expectedSummer))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].autumnUrl").value(expectedAutumn))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].winterUrl").value(expectedWinter))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].springUrl").value(expectedSpring))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].seasonStatus").value("SUMMER"));
    }

    @Test
    @DirtiesContext
    void expectUpdatedTheme_whenUpdatingTheme() throws Exception {
        //Given
        String expected = """
                           {
                               "id": "12344445",
                               "name": "Theme Theme",
                               "springUrl": "https://cdn.discordapp.com/attachments/1090325789939085312/1123893739421708328/00038-162447185.png",
                               "summerUrl": "https://cdn.discordapp.com/attachments/1090325789939085312/1123893755842412616/00001-918857782.png",
                               "autumnUrl": "https://cdn.discordapp.com/attachments/1090325789939085312/1123893768257540137/00043-3644440715.png",
                               "winterUrl": "https://cdn.discordapp.com/attachments/1090325789939085312/1123893783323488316/00006-1847996727.png",
                               "seasonStatus": "WINTER"
                            }
                        """;

        //When
        mockMvc.perform(MockMvcRequestBuilders.put("/api/theme")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testThemeJson))

        //Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));

    }

    @Test
    @DirtiesContext
    void expectThemeWithPathIdIsDeleted_whenDeletingTheme() throws Exception {
        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/theme/12344445"))
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertEquals(0, themeRepo.getThemes().size());
    }

}
