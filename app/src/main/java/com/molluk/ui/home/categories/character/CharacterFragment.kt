package com.molluk.ui.home.categories.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.molluk.databinding.FragmentCharacterBinding
import com.molluk.ui.base.BaseFragment
import androidx.navigation.fragment.navArgs
import com.molluk.BR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : BaseFragment() {
    private lateinit var binding: FragmentCharacterBinding
    private val args: CharacterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        binding.setVariable(BR.data, args.data)
        return binding.root
    }
}