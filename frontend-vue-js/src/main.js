import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import router from './plugins/router';
import axios from "axios";
import VueAxios from "vue-axios";
import VueLayers from 'vuelayers'
import 'vuelayers/lib/style.css'

Vue.use(VueLayers)
Vue.use(VueAxios, axios);

Vue.config.productionTip = false

import "@/assets/background.css"

new Vue({
  vuetify,
  router,
  render: h => h(App)
}).$mount('#app')
