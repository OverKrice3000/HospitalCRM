import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'login',
    meta: {layout: 'empty'},
    component: () => import('../views/Login')
  },
  {
    path: '/',
    name: 'patients',
    meta: {layout: 'main'},
    component: () => import('../views/Patients')
  },
  {
    path: '/appointments',
    name: 'appointments',
    meta: {layout: 'main'},
    component: () => import('../views/Appointments')
  },
  {
    path: '/doctors',
    name: 'doctors',
    meta: {layout: 'main'},
    component: () => import('../views/Doctors')
  },
  {
    path: '/departments',
    name: 'departments',
    meta: {layout: 'main'},
    component: () => import('../views/Departments')
  },
  {
    path: '/orders',
    name: 'orders',
    meta: {layout: 'main'},
    component: () => import('../views/Orders')
  },
  {
    path: '/medicine',
    name: 'medicine',
    meta: {layout: 'main'},
    component: () => import('../views/Medicine')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
