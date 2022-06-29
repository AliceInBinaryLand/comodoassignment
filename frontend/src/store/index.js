import Vue from "vue";
import Vuex from "vuex";

import layout from "./layout.module";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    message: {
      content: "",
      type: "",
    },
  },
  mutations: {
    SET_MESSAGE(state, payload) {
      state.message.content = payload.content;
      state.message.type = payload.type;
    },
  },
  actions: {},
  modules: {
    layout,
  },
});
