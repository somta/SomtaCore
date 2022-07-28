package net.somta.demo.web.controller;

import net.somta.core.base.result.ResponseDataResult;
import net.somta.search.impl.ISearchTemplate;
import net.somta.search.model.Field;
import net.somta.search.model.FieldTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Date:        2022-07-12
 * @Author:      husong
 * @Version:     1.0.0
 */
@RestController
@RequestMapping("/v1/search")
public class SearchController {

    @Autowired
    private ISearchTemplate searchTemplate;

    /**
     * 测试
     * @return
     * @throws Exception
     */
    @GetMapping("/testIndex")
    public ResponseDataResult<String> testIndex() throws Exception{
        String indexName = "test-hs";
        // 新增索引
        Map<String, Field> columnMap = new HashMap<>();

        columnMap.put("name",new Field(FieldTypeEnum.BASE_TEXT.getValue()));
        columnMap.put("age",new Field(FieldTypeEnum.BASE_INTEGER.getValue()));
        // 创建索引
        searchTemplate.createIndex(indexName,columnMap);

        // 插入文档数据
        String id = "123";
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name","husong");
        map.put("age",33);
        searchTemplate.insertDocument(indexName,"id",map);

        // 查询文档数据
        String doc = searchTemplate.getDocumentById(indexName,id);
        return ResponseDataResult.setResponseResult("success:"+doc);
    }

}
