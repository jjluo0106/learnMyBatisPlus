package org.py.mymodule.submodule.service.impl;

import org.py.mymodule.submodule.entity.Dept;
import org.py.mymodule.submodule.mapper.DeptMapper;
import org.py.mymodule.submodule.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2025-04-23
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

}
