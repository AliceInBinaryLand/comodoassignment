import $axios from "@/plugins/axios";

/**
 * Service to call HTTP request via Axios
 */
const ApiService = {
  query(resource, params) {
    return $axios.get(resource, params).catch(() => {});
  },

  /**
   * Send the GET HTTP request
   * @param resource
   * @param slug
   * @returns {*}
   */
  get(resource, slug = "") {
    return $axios.get(`${resource}/${slug}`).catch(() => {});
  },

  /**
   * Set the POST HTTP request
   * @param resource
   * @param params
   * @param config
   * @returns {*}
   */
  post(resource, params, config) {
    return $axios.post(`${resource}`, params, config);
  },

  /**
   * Send the UPDATE HTTP request
   * @param resource
   * @param slug
   * @param params
   * @returns {IDBRequest<IDBValidKey> | Promise<void>}
   */
  update(resource, slug, params) {
    return $axios.put(`${resource}/${slug}`, params);
  },

  /**
   * Send the PUT HTTP request
   * @param resource
   * @param params
   * @returns {IDBRequest<IDBValidKey> | Promise<void>}
   */
  put(resource, params) {
    return $axios.put(`${resource}`, params);
  },

  /**
   * Send the DELETE HTTP request
   * @param resource
   * @returns {*}
   */
  delete(resource) {
    return $axios.delete(resource).catch(() => {});
  },
};

export default ApiService;
