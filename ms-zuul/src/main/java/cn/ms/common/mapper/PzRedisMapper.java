package cn.ms.common.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import cn.ms.common.schema.PzRedis;

public interface PzRedisMapper  {


	/**
	 * 根据主键删除
	 */
	@Delete(value = { "<script>", "DELETE FROM Pz_Redis WHERE id=#{0}", "</script>" })
	int deleteById(Long id);

	/**
	 * 根据主键查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM Pz_Redis WHERE id=#{0}", "</script>" })
	PzRedis selectById(Long id);
	
	@Select(value = { "<script>", "SELECT * FROM Pz_Redis", "</script>" })
	PzRedis selectAll();

}
