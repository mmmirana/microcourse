package ${daoImplPackageName};

import org.springframework.stereotype.Repository;

import com.mcourse.frame.base.dao.BaseDaoImpl;
import ${daoClassName};
import ${modelClassName};

/**
 * ${daoImplFileName}
 * 
 * @Title
 * @Description
 * 
 * @CreatedBy Assassin_DBGenerator
 * @DateTime ${datetime}
 */
@Repository("${modelNameLowercase}Dao")
public class ${daoImplClassSimpleName} extends BaseDaoImpl<${modelClassSimpleName}> implements ${daoClassSimpleName} {

}
