package com.lin.mvc.util;

import java.util.*;

/**
 * Created by K Lin on 2017/7/16.
 */
public class UserMap<K,V>
{
    public Map<K,V> map= Collections.synchronizedMap(new HashMap<K, V>());
    //根据value删除指定项
    public synchronized void removeByValue(Object value)
    {
        for(Object key:map.keySet())
        {
            if(map.get(key)==value)
            {
                map.remove(key);
                break;
            }
        }
    }
    public synchronized Set<K> getKey()
    {
        return map.keySet();
    }
    //获取所有value组成的Set集合
    public synchronized Set<V> valueSet()
    {
        Set<V> result=new HashSet<V>();
        //将map中所有的value添加到result集合中
        for(Object key:map.keySet()) {
            result.add(map.get(key));
        }
        return result;
    }

    //根据value查找key
    public synchronized K getKeyByValue(V val)
    {
        for(K key:map.keySet())
        {
            if(map.get(key)==val||map.get(key).equals(val))
            {
                return key;
            }
        }
        return null;
    }

    //实现put()方法，该方法不允许value重复
    public synchronized V put(K key,V value)
    {
        for(V val:valueSet())
        {
            if (val.equals(value)&&val.hashCode()==value.hashCode())
            {
                throw new RuntimeException("该输入以操作");
            }
        }
        return map.put(key,value);
    }
}
