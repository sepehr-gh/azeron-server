package io.pinect.azeron.server.service;

import io.pinect.azeron.server.domain.dto.ResponseStatus;
import io.pinect.azeron.server.domain.dto.out.InfoPublishDto;
import io.pinect.azeron.server.domain.dto.out.InfoResultDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InfoService {
    private final Map<String, InfoResultDto.InfoResult> resultMap = new ConcurrentHashMap<>();

    public void addInfo(InfoPublishDto infoPublishDto){
        InfoResultDto.InfoResult infoResult = getInfoResult(infoPublishDto);
        resultMap.put(infoResult.getServerUUID(), infoResult);
    }

    public InfoResultDto getInfoResultDto(){
        List<InfoResultDto.InfoResult> infoResults = new ArrayList<>();
        resultMap.forEach((s, infoResult) -> {
            infoResults.add(infoResult);
        });

        InfoResultDto infoResultDto = InfoResultDto.builder().results(infoResults).build();
        infoResultDto.setStatus(ResponseStatus.OK);
        return infoResultDto;
    }

    private InfoResultDto.InfoResult getInfoResult(InfoPublishDto infoPublishDto) {
        return InfoResultDto.InfoResult.builder()
                .nats(infoPublishDto.getNats())
                .serverUUID(infoPublishDto.getServerUUID())
                .channelsCount(infoPublishDto.getChannelsCount())
                .build();
    }
}
