package ua.com.servio.statisticservio.service.sync;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ua.com.servio.statisticservio.model.json.AuthPerson;
import ua.com.servio.statisticservio.model.json.AuthResult;

import static ua.com.servio.statisticservio.common.Consts.AUTH_PATTERN_URL;

public interface SyncService {

    @POST(AUTH_PATTERN_URL)
    Observable<AuthResult> authPerson(@Body AuthPerson authPerson);

//    @GET
//    Observable<SummaryStatic> getSummaryStatic(@Url String url);


}
