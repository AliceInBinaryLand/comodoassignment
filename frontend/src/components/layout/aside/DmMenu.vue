<template>
  <v-list dense nav>
    <v-list-item
      v-for="item in getMenuItems"
      :key="item.title"
      link
      :to="item.url ? item.url : null"
      :disabled="!item.url"
      active-class="warning--text"
    >
      <template v-if="item.url">
        <v-list-item-icon class="mr-2">
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item-content>
      </template>
      <template v-else>
        <v-list-item-title v-if="!getMini">
          {{ item.title }}
        </v-list-item-title>
      </template>
    </v-list-item>
  </v-list>
</template>

<script>
import { mapGetters } from "vuex";
import store from "@/store";

export default {
  computed: {
    ...mapGetters({
      getMenuItems: "layout/getMenuItems",
      getMini: "layout/getMini",
    }),
  },
  methods: {},
};
</script>

<style scoped></style>
