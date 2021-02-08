package com.achilles.wild.server.business.dao;

import com.achilles.wild.server.business.entity.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictDao {

    int insertSelective(Dict dict);

    List<Dict> selectList(@Param("group") String group,@Param("key") String key);
}
