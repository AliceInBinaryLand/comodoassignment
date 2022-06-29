import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";

import "./assets/css/fonts.css";
import "@/plugins/validation";
import $axios from "@/plugins/axios";
import toastPlugin from "@/plugins/toaster";
import "roboto-fontface/css/roboto/roboto-fontface.css";
import "@mdi/font/css/materialdesignicons.css";
import { DateTime } from "luxon";
import "core-js/stable";
import "regenerator-runtime/runtime";


Vue.use(toastPlugin, { store });

Vue.prototype.dt = DateTime;
Vue.prototype.$axios = $axios;

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
