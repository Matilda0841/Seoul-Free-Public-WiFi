package service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class WifiServiceTest {

    @DisplayName("응답값이 200이여함")
    @Test
    void 와이파이정보API_호출() throws IOException {
        //given
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
        urlBuilder.append("/" + URLEncoder.encode("4370764a516d616c38377342586e4f", "UTF-8")); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        // when
        int code = conn.getResponseCode();

        //then
        assertThat(code).isEqualTo(200);
    }

    @DisplayName("파싱한 결과는 hello word와 같아야 한다.")
    @Test
    void gson사용_파싱() {
        //given
        String str = "{\"test\":\"hello word\"}";
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(str);
        jsonElement = jsonElement.getAsJsonObject().get("test");

        //when
        String result = jsonElement.getAsString();

        //then
        assertThat(result).isEqualTo("hello word");

    }




}