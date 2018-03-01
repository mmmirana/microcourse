package ${serviceImplPackageName};

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ${iBaseDaoPackname};
import ${baseServiceImplPackname};
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
