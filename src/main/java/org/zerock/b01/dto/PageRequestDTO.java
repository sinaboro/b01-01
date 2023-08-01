package org.zerock.b01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page=1;

    @Builder.Default
    private int size=10;

    private String type;  //검색 t,c, w, tc,tw,   tcw
    private String keyword; //검색 단어   java

    public String[] getTypes(){  // tcw   -> t c w ==> 문자열을 개별 문자로 분해해서 스트링 배열 저장
        if(type == null || type.isEmpty()){
            return null;
        }
        return type.split("");
    }

    // abc, def       ; abc  ; aaa, bbb, ccc
    public Pageable getPageable(String ...props) { //가변인자..
        return PageRequest.of(this.page -1, this.size, Sort.by(props).descending());
    }

    private String link;

    public String getLink() {   //검색조건과 페이징 조건을 문자열로 구성

        if(link == null){
            StringBuilder builder = new StringBuilder();

            builder.append("page=" + this.page);   //page=0

            builder.append("&size=" + this.size);  //page=0&size=10

            if(type != null && type.length() > 0){
                builder.append("&type=" + type);   //page=0&size=10&type=tcw
            }

            if(keyword != null){
                try {  //page=0&size=10&type=tcw&keyword=java
                    builder.append("&keyword=" + URLEncoder.encode(keyword,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                }
            }
            link = builder.toString(); // "page=0&size=10&type=tcw&keyword=java"
        }
        return link;
    }
}
