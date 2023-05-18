import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '校园社区管理系统',
    meta: { title: '校园社区管理系统' },
    // hidden: true,
    children: [{
      path: 'dashboard',
      meta: { title: '校园社区管理系统' },
      component: () => import('@/views/dashboard/index')
    }]
  },

  {
    path: '/user',
    component: Layout,
    redirect: '/user/list',
    name: 'Example',
    meta: { title: '用户管理列表', icon: 'user' },
    children: [
      {
        path: 'list',
        name: '用户管理列表',
        component: () => import('@/views/user/list'),
        meta: { title: '用户管理列表', icon: 'user' }
      }
    ]
  },

  // //家教信息后台管理-路由8002
  // {
  //   path: '/eduMessage',
  //   component: Layout,
  //   redirect: '/eduMessage/table', //默认访问地址
  //   name: '家教信息管理',
  //   meta: { title: '家教信息管理', icon: 'example' },
  //   children: [
  //     {
  //       path: 'table',
  //       name: '家教信息列表',
  //       component: () => import('@/views/edu/eduMessage/list'),
  //       meta: { title: '家教信息列表', icon: 'table' }
  //     }
  //   ]
  // },
  //兼职信息后台管理-路由8002
  {
    path: '/message',
    component: Layout,
    redirect: '/message/table', //默认访问地址
    name: '兼职信息管理',
    meta: { title: '兼职信息管理', icon: 'example' },
    children: [
      {
        path: 'table',
        name: '兼职信息列表',
        component: () => import('@/views/message/list'),
        meta: { title: '兼职信息列表', icon: 'table' }
      }
    ]
  },
  //商品信息后台管理-路由8005
  {
    path: '/good',
    component: Layout,
    redirect: '/good/list', //默认访问地址
    name: '商品信息管理',
    meta: { title: '商品信息管理', icon: 'form' },
    children: [
      {
        path: 'list',
        name: '商品分类列表',
        component: () => import('@/views/good/list'),
        meta: { title: '商品信息管理', icon: 'form' }
      }
    ]
  },
  //公告信息后台管理-路由8004
  {
    path: '/notice',
    component: Layout,
    redirect: '/notice/list', //默认访问地址
    name: '公告信息管理',
    meta: { title: '公告信息管理', icon: 'example' },
    children: [
      {
        path: 'list',
        name: '公告分类列表',
        component: () => import('@/views/notice/list'),
        meta: { title: '公告分类管理', icon: 'table' }
      },
      {
        path: 'save',
        name: '添加公告',
        component: () => import('@/views/notice/save'),
        meta: { title: '添加公告', icon: 'table' }
      }
    ]
  },
  //动态信息后台管理-路由8006
  {
    path: '/trend',
    component: Layout,
    redirect: '/trend/list', //默认访问地址
    name: '动态信息管理',
    meta: { title: '动态信息管理', icon: 'link' },
    children: [
      {
        path: 'list',
        name: '动态信息列表',
        component: () => import('@/views/trend/list'),
        meta: { title: '动态信息管理', icon: 'link' }
      }
    ]
  },
  // {
  //   path: '/form',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       name: 'Form',
  //       component: () => import('@/views/form/index'),
  //       meta: { title: 'Form', icon: 'form' }
  //     }
  //   ]
  // },

  // {
  //   path: '/nested',
  //   component: Layout,
  //   redirect: '/nested/menu1',
  //   name: 'Nested',
  //   meta: {
  //     title: 'Nested',
  //     icon: 'nested'
  //   },
  //   children: [
  //     {
  //       path: 'menu1',
  //       component: () => import('@/views/nested/menu1/index'), // Parent router-view
  //       name: 'Menu1',
  //       meta: { title: 'Menu1' },
  //       children: [
  //         {
  //           path: 'menu1-1',
  //           component: () => import('@/views/nested/menu1/menu1-1'),
  //           name: 'Menu1-1',
  //           meta: { title: 'Menu1-1' }
  //         },
  //         {
  //           path: 'menu1-2',
  //           component: () => import('@/views/nested/menu1/menu1-2'),
  //           name: 'Menu1-2',
  //           meta: { title: 'Menu1-2' },
  //           children: [
  //             {
  //               path: 'menu1-2-1',
  //               component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
  //               name: 'Menu1-2-1',
  //               meta: { title: 'Menu1-2-1' }
  //             },
  //             {
  //               path: 'menu1-2-2',
  //               component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
  //               name: 'Menu1-2-2',
  //               meta: { title: 'Menu1-2-2' }
  //             }
  //           ]
  //         },
  //         {
  //           path: 'menu1-3',
  //           component: () => import('@/views/nested/menu1/menu1-3'),
  //           name: 'Menu1-3',
  //           meta: { title: 'Menu1-3' }
  //         }
  //       ]
  //     },
  //     {
  //       path: 'menu2',
  //       component: () => import('@/views/nested/menu2/index'),
  //       meta: { title: 'menu2' }
  //     }
  //   ]
  // },

  // {
  //   path: 'external-link',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
  //       meta: { title: 'External Link', icon: 'link' }
  //     }
  //   ]
  // },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
