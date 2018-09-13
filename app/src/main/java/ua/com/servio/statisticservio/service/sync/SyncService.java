package ua.com.servio.statisticservio.service.sync;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ua.com.servio.statisticservio.model.json.AuthPerson;
import ua.com.servio.statisticservio.model.json.AuthResult;
import ua.com.servio.statisticservio.model.json.DownloadResponse;
import ua.com.servio.statisticservio.model.json.UploadResponse;

import static ua.com.servio.statisticservio.common.Consts.AUTH_PATTERN_URL;
import static ua.com.servio.statisticservio.common.Consts.SUMMARY_STATIC_PATTERN_URL;

public interface SyncService {

    @POST(AUTH_PATTERN_URL)
    Observable<AuthResult> authPerson(@Body AuthPerson authPerson);

    @POST(SUMMARY_STATIC_PATTERN_URL)
    Observable<DownloadResponse> getSummaryStatic(@Body UploadResponse uploadResponse);


}
