<template>
  <v-app>
    <dm-aside></dm-aside>
    <dm-header></dm-header>
    <v-main>
      <dm-toast></dm-toast>
      <v-container fluid>
        <v-slide-x-transition mode="out-in">
          <router-view> </router-view>
        </v-slide-x-transition>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import DmAside from "@/components/layout/aside/DmAside";
import DmHeader from "@/components/layout/header/DmHeader";
import { mapGetters } from "vuex";
import DmToast from "@/components/common/DmToast";
export default {
  name: "layout-main",

  components: {
    DmToast,
    DmHeader,
    DmAside,
  },

  data: () => ({}),
  computed: {
    ...mapGetters({
      isAuthenticated: "auth/isAuthenticated",
    }),
  },
  methods: {},
};
</script>
<style lang="scss"></style>
