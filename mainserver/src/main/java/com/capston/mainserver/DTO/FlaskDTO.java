package com.capston.mainserver.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class FlaskDTO {

    @Getter
    @Setter
    public static class clientDTO {
        private int pageIndex;
        private Long documentId;
    }

    @Getter
    @Setter
    public static class requestDTO {
        private String image;

        public String[] getImage() {
            return image.split(",");
        }
    }
}
