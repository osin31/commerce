package kr.co.abcmart.zconfiguration.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.abcmart.trace.SQLTrace;
import kr.co.abcmart.trace.TraceContext;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @Desc : Mybatis sql 값을 추적.
 * @FileName : MybatisSelectInterceptor.java
 * @Project : abc.bo
 * @Date : 2019. 3. 13.
 * @Author : 장진철 (zerocooldog@zen9.co.kr)
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
@Slf4j
public class MybatisSelectInterceptor implements Interceptor {

	/** InvocationTargetException는 로직에서 잡아서 처리하는 경우가 있음으로 그냥 던져준다. */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		SQLTrace sqlTrace = TraceContext.getContext();

		if (sqlTrace == null) {
			return invocation.proceed();
		}

		Object[] args = invocation.getArgs();
		long startTime = System.currentTimeMillis();
		long elapsed = 0;
		Object result = null;

		try {
			result = invocation.proceed();
		} finally {

			elapsed = (System.currentTimeMillis() - startTime);

			MappedStatement mappedStatement = (MappedStatement) args[0];
			BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(args[1]);
			List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

			Map<String, Object> parameter = new HashMap<>();
			if (parameterMappings != null) {
				for (ParameterMapping parameterMapping : parameterMappings) {
					String parameterName = parameterMapping.getProperty();
					parameter.put(parameterName, boundSql.getParameterObject());
					log.debug("value : {}", boundSql.getAdditionalParameter(parameterName));
					// BeanUtils.getProperty 오류로 주석 - 해당 property만 추출하는건 나중에...
//					try {
//						parameter.put(parameterName,
//								BeanUtils.getProperty(boundSql.getParameterObject(), parameterName));
//					} catch (Exception e) {
//						parameter.put(parameterName, BeanUtils.getProperty(boundSql.getParameterObject(), null));
//					}
				}
			}
			String sql = boundSql.getSql();
			ObjectMapper objMap = new ObjectMapper();

			if(sql.indexOf("@NoAccessHis") <= 0 && sql.indexOf("count(1) as total_count") <= 0  && sql.indexOf("MB_MEMBER") > 0) {
				int idx1 = sql.indexOf('[');
				int idx2 = sql.indexOf(']');

				SQLTrace newSqlTrace = new SQLTrace();

				if(idx1 > 0 && idx2 > 0) {
					newSqlTrace.setSucessRsnText(sql.substring(idx1+1, idx2));
				}else {
					newSqlTrace.setSucessRsnText(mappedStatement.getId());
				}

				newSqlTrace.setQueryId(mappedStatement.getId());
				newSqlTrace.setQueryText(sql);
				newSqlTrace.setAccessType(mappedStatement.getSqlCommandType().toString().subSequence(0, 1)+"00");
				newSqlTrace.setAccessParameterText(parameter.toString());
				newSqlTrace.setAccessUrl(sqlTrace.getAccessUrl());
				newSqlTrace.setRsltCount(getSizeReturnObject(result));
				newSqlTrace.setAccessUrl(sqlTrace.getAccessUrl());
				newSqlTrace.setMenuYn(sqlTrace.getMenuYn());
				newSqlTrace.setAccessUrl(sqlTrace.getAccessUrl());
				newSqlTrace.setAccessIpText(sqlTrace.getAccessIpText());
				newSqlTrace.setSucessYn(sqlTrace.getSucessYn());
				newSqlTrace.setRsltDescText(objMap.writeValueAsString(result));

				TraceContext.setSqlTrace(newSqlTrace);
			}

			if(elapsed >= 20000) {
				log.debug("20초 초과 쿼리 : {}", mappedStatement.getId() );
			}
		}

		return result;
	}

	/***
	 * 파라메터 갯수 호출.
	 *
	 * @Desc :
	 * @Method Name : getSizeReturnObject
	 * @Date : 2019. 3. 12.
	 * @Author : user
	 * @param object
	 * @return
	 */
	private int getSizeReturnObject(Object object) {

		if (object != null) {
			if (object.getClass().isArray()) {
				return ((Object[]) object).length;
			} else if (object instanceof List) {
				return ((List) object).size();
			} else if (object instanceof Map) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// 무시
	}

}