package org.zerock.apiserver.domain.Discord.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class DiscordService {

    @Value("${discord.500.webhook-uri}")
    private String webhook5xxUrl;

    @Value("${discord.400.webhook-uri}")
    private String webhook4xxUrl;

    @Qualifier("discordWebClient")
    private final WebClient discordWebClient;

    // ========================
    // 500 에러 알림
    // ========================
    @Async("threadPoolTaskExecutor")
    public void send5xxNotification(String errorMessage, String stackTrace, String clientInfo, String requestInfo) {
        log.info("discord 5xx 알림 처리 시작 {}", webhook5xxUrl);
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String errMsg = (errorMessage == null || errorMessage.isEmpty()) ? "Unknown Error" : errorMessage;

            String method = "";
            String url = "";
            if (requestInfo != null && requestInfo.contains(" ")) {
                String[] parts = requestInfo.split(" ");
                if (parts.length >= 2) {
                    method = parts[0];
                    url = parts[1];
                }
            }

            String st = (stackTrace == null)
                ? "None"
                : (stackTrace.length() > 1500 ? stackTrace.substring(0, 1500) + "..." : stackTrace);

            StringBuilder message = new StringBuilder();
            message.append("```")
                .append("\n┌─ 🔥 Server Error 🔥")
                .append("\n│ time    : ").append(timestamp)
                .append("\n│ method  : ").append(method)
                .append("\n│ url     : ").append(url)
                .append("\n│ message : ").append(errMsg);
            if (clientInfo != null && !clientInfo.isEmpty()) {
                message.append("\n│ client  : ").append(clientInfo);
            }
            message.append("\n│ stack  : below")
                .append("\n└─ \n")
                .append(st)
                .append("\n```");

            discordWebClient.post()
                .uri(webhook5xxUrl)
                .bodyValue(Map.of(
                    "content", message.toString(),
                    "username", "🚨 500대 에러 발생 🚨"
                ))
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(res -> log.info("✅ Discord 5xx 알림 전송 성공"))
                .doOnError(err -> log.error("❌ Discord 5xx 알림 전송 실패: {}", err.getMessage()))
                .subscribe();

        } catch (Exception e) {
            log.error("Discord 5xx 알림 처리 중 예외 발생: {}", e.getMessage(), e);
        }
    }

    // ========================
    // 400 에러 알림
    // ========================
    @Async("threadPoolTaskExecutor")
    public void send4xxNotification(String errorMessage, String requestInfo, String clientInfo) {
        log.info("discord 4xx 알림 처리 시작 {}", webhook4xxUrl);
        try {
            if (webhook4xxUrl == null || webhook4xxUrl.isEmpty()) {
                log.debug("400대 에러 웹훅 URL이 설정되지 않음");
                return;
            }

            // 기본 메시지 구성
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String errMsg = (errorMessage == null || errorMessage.isEmpty()) ? "Unknown Error" : errorMessage;

            // 요청 정보 파싱
            String method = "";
            String url = "";
            if (requestInfo != null && requestInfo.contains(" ")) {
                String[] parts = requestInfo.split(" ");
                if (parts.length >= 2) {
                    method = parts[0];
                    url = parts[1];
                }
            }

            // 디스코드 박스 포맷 문자열
            StringBuilder message = new StringBuilder();
            message.append("```")
                .append("\n┌─ 🔥 Client Error 🔥")
                .append("\n│ time    : ").append(timestamp)
                .append("\n│ method  : ").append(method)
                .append("\n│ url     : ").append(url)
                .append("\n│ message : ").append(errMsg);

            if (clientInfo != null && !clientInfo.isEmpty()) {
                message.append("\n│ client  : ").append(clientInfo);
            }

            message.append("\n└─")
                .append("```");

            // 전송
            discordWebClient.post()
                .uri(webhook4xxUrl)
                .bodyValue(Map.of(
                    "content", message.toString(),
                    "username", "💬 400대 에러 발생 💬"
                ))
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(res -> log.info("✅ Discord 4xx 알림 전송 성공"))
                .doOnError(err -> log.error("❌ Discord 4xx 알림 전송 실패: {}", err.getMessage()))
                .subscribe();

        } catch (Exception e) {
            log.error("Discord 4xx 알림 처리 중 예외 발생: {}", e.getMessage(), e);
        }
    }

}
