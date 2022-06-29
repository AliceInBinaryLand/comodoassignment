<template>
  <v-menu
    ref="menu"
    v-model="menu"
    :return-value.sync="innerValue"
    :close-on-content-click="false"
    transition="scale-transition"
    offset-y
    min-width="auto"
  >
    <template v-slot:activator="{}">
      <ValidationProvider
        :vid="$attrs.vid"
        :name="$attrs.vName"
        :mode="$attrs.mode || 'eager'"
        :rules="rules"
        v-slot="{ errors, valid }"
      >
        <v-text-field
          v-model="innerValue"
          :error-messages="errors"
          :success="valid"
          prepend-inner-icon="mdi-calendar"
          clearable
          readonly
          v-bind="$attrs"
          v-on="$listeners"
        ></v-text-field>
      </ValidationProvider>
    </template>

    <v-date-picker
      v-model="innerValue"
      no-title
      scrollable
      v-bind="$attrs"
      :range="$attrs.range"
      :min="min"
      :max="max"
      v-on="$listeners"
    >
      <v-spacer></v-spacer>
      <v-btn text color="primary" @click="menu = false"> Cancel </v-btn>
      <v-btn text color="primary" @click="$refs.menu.save(innerValue)">
        OK
      </v-btn>
    </v-date-picker>
  </v-menu>
</template>

<script>
import { ValidationProvider } from "vee-validate";
import moment from "moment";

export default {
  components: {
    ValidationProvider,
  },
  props: {
    rules: {
      type: [Object, String],
      default: "",
    },
    // must be included in props
    value: {
      type: null,
    },
    min: {
      type: null,
    },
    max: {
      type: null,
    },
    vMask: {
      type: null,
    },
  },
  data: () => ({
    innerValue: "",
    menu: false,
  }),
  watch: {
    // Handles internal model changes.
    innerValue(newVal) {
      this.$emit("input", newVal);
    },
    // Handles external model changes.
    value(newVal) {
      this.innerValue = newVal;
    },
  },
  computed: {
    formattedDate() {
      return this.innerValue
        ? moment(this.innerValue).format("DD/MM/YYYY")
        : "";
    },
  },
  created() {
    if (this.value) {
      this.innerValue = this.value;
    }
  },
};
</script>
