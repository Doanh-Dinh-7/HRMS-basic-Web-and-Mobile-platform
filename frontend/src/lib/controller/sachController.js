export const BASE_URL =
  import.meta.env.MODE === "development"
    ? import.meta.env.VITE_API_BASE_URL
    : "/api";

export const getAllSach = async () => {
  try {
    const res = await fetch(BASE_URL + "/search");
    const data = await res.json();
    if (!res.ok) {
      throw new Error(data.error);
    }
    return data;
  } catch (error) {
    console.log(error);
  }
};

export const getTenSach = async (tensach) => {
  try {
    const res = await fetch(BASE_URL + "/search/tensach/" + tensach);
    const data = await res.json();
    if (!res.ok) {
      throw new Error(data.error);
    }
    return data;
  } catch (error) {
    console.log(error);
  }
};
