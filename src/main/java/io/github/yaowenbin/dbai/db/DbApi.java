package io.github.yaowenbin.dbai.db;

import io.github.yaowenbin.dbai.common.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DbApi {

    private final DbService dbService;


    @PostMapping("/datasources")
    public R<Void> createDs(@RequestBody DataSourceCreateRequest request) {
        dbService.createDataSource(request);
        return R.success();
    }

    @PostMapping("/datasources/{dsKey}/initialize")
    public R<Void> initializeDb(@PathVariable String dsKey, @RequestBody DataInitializeReq request) {
        dbService.initialize(dsKey, request.getSql());
        return R.success();
    }

}
