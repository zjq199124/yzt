

import re


def process_symptoms(symptom_str):
    symptom_list = []
    symptom_str = symptom_str \
        .replace(" ", "") \
        .replace("或", "") \
        .replace("伴有", "") \
        .replace("等","")
    arr = re.split("[,.;，。；、]", symptom_str)
    for it in arr:
        symptom = it.strip()
        if len(symptom) > 1:
            symptom_list.append(symptom)
    return symptom_list