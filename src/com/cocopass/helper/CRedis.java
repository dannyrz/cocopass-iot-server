package com.cocopass.helper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**   
 * Redis操作接口
 *
 * @author 林计钦
 * @version 1.0 2013-6-14 上午08:54:14   
 */
public class CRedis {
    private static JedisPool pool = null;
    
    /**
     * 构建redis连接池
     * 
     * @param ip
     * @param port
     * @return JedisPool
     */
    public static JedisPool StartPool(int maxActive,int maxIdle,long maxWaitMillis,String host,int port,int timeOut,String password) {
        if (pool == null) {
        	
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(maxActive); //config.setMaxActive(500);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(maxIdle);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(maxWaitMillis);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, host, port,timeOut,password);

        }
        return pool;
    }
    
    public static JedisPool GetPool()
    {
    	return pool;
    }
    
    /**
     * 返还到连接池
     * 
     * @param pool 
     * @param redis
     */
    public static void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null) {
            pool.returnResource(redis);
        }
    }
    
    /**
     * 获取数据
     * @param key
     * @return
     */
    public static String get(String key){
        String value = null;
        
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = GetPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        
        return value;
    }
    /**
     * 获取数据
     * @param key
     * @return
     */
    public static String set(String key,String value){
         
        String result="";
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = GetPool();
            jedis = pool.getResource();
            result = jedis.set(key,value);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        
        return result;
    }
    
    public static String setex(String key,int seconds,String value){
        
        String result="";
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = GetPool();
            jedis = pool.getResource();
            result = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        
        return result;
    }
    
    public static long  SetMapValue(String key,String field,String value){
    	JedisPool pool = null;
        Jedis jedis = null;
        long id=0;
        try {
            pool = GetPool();
            jedis = pool.getResource();
            id = jedis.hset(key, field, value);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        return id;
    }
    public static String getMapValue(String key,String field){
        String value = null;
        
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = GetPool();
            jedis = pool.getResource();
            value = jedis.hget(key, field);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        
        return value;
    }
}