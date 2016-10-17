package com.cocopass.helper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**   
 * Redis�����ӿ�
 *
 * @author �ּ���
 * @version 1.0 2013-6-14 ����08:54:14   
 */
public class CRedis {
    private static JedisPool pool = null;
    
    /**
     * ����redis���ӳ�
     * 
     * @param ip
     * @param port
     * @return JedisPool
     */
    public static JedisPool StartPool(int maxActive,int maxIdle,long maxWaitMillis,String host,int port,int timeOut,String password) {
        if (pool == null) {
        	
            JedisPoolConfig config = new JedisPoolConfig();
            //����һ��pool�ɷ�����ٸ�jedisʵ����ͨ��pool.getResource()����ȡ��
            //�����ֵΪ-1�����ʾ�����ƣ����pool�Ѿ�������maxActive��jedisʵ�������ʱpool��״̬Ϊexhausted(�ľ�)��
            config.setMaxTotal(maxActive); //config.setMaxActive(500);
            //����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ����
            config.setMaxIdle(maxIdle);
            //��ʾ��borrow(����)һ��jedisʵ��ʱ�����ĵȴ�ʱ�䣬��������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException��
            config.setMaxWaitMillis(maxWaitMillis);
            //��borrowһ��jedisʵ��ʱ���Ƿ���ǰ����validate���������Ϊtrue����õ���jedisʵ�����ǿ��õģ�
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
     * ���������ӳ�
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
     * ��ȡ����
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
            //�ͷ�redis����
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //���������ӳ�
            returnResource(pool, jedis);
        }
        
        return value;
    }
    /**
     * ��ȡ����
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
            //�ͷ�redis����
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //���������ӳ�
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
            //�ͷ�redis����
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //���������ӳ�
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
            //�ͷ�redis����
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //���������ӳ�
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
            //�ͷ�redis����
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //���������ӳ�
            returnResource(pool, jedis);
        }
        
        return value;
    }
}