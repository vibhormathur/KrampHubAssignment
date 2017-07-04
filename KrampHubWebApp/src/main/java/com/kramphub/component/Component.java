package com.kramphub.component;

import java.util.List;

import com.kramphub.entity.RespEntity;

public interface Component {

	public List<RespEntity> execute(String term, Integer limit);

}
