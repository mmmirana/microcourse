package ${serviceImplPackageName};

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mcourse.frame.base.dao.IBaseDao;
import com.mcourse.frame.base.service.BaseServiceImpl;
import ${serviceClassName};
import ${modelClassName};

/**
 * ${serviceImplFileName}
 * 
 * @Title
 * @Description
 * 
 * @CreatedBy Assassin_DBGenerator
 * @DateTime ${datetime}
 */
@Service("${modelNameLowercase}Service")
public class ${serviceImplClassSimpleName} extends BaseServiceImpl<${modelClassSimpleName}> implements ${serviceClassSimpleName} {

	@Resource(name = "${modelNameLowercase}Dao")
	@Override
	public void setBaseDao(IBaseDao<${modelClassSimpleName}> baseDao) {
		super.setBaseDao(baseDao);
	}

}
