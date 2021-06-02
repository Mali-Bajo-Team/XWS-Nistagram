import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

import User from './../pages/user/Container';
import UserLanding from './../pages/user/landing/Landing';
import Login from './../pages/user/login/Login';
import Registration from './../pages/user/registration/Registration';
import Profile from './../pages/user/profile/Profile';


const routes = [
    {
        component: User,
        path: '/',
        meta: {
            user: true
        },
        children: [
            {
                component: UserLanding,
                name: 'user',
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
            {
                component:Profile,
                name:'profile',
                path:'profile'
            }
            
        ]
    },
   
    
]

let router = new VueRouter({
    routes
});

export default router
