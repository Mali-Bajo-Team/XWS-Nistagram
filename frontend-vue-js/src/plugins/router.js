import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

import Guest from './../pages/guest/Container';
import GuestLanding from './../pages/guest/landing/Landing';
import Login from './../pages/guest/login/Login';
import Registration from './../pages/guest/registration/Registration';


const routes = [
    {
        component: Guest,
        path: '/',
        meta: {
            guest: true
        },
        children: [
            {
                component: GuestLanding,
                name: 'guest',
                path: ''
            },
            {
                component: Login,
                name: 'login',
                path: 'login'
            },
            {
                component: Registration,
                name: 'registration',
                path: 'register'
            },
            
        ]
    },
   
    
]

let router = new VueRouter({
    routes
});

export default router
